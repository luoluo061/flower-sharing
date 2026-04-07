param(
    [string]$JarPath = 'E:\flower-sharing\ruoyi-admin\target\ruoyi-admin.jar',
    [string]$BaseUrl = 'http://127.0.0.1:8080',
    [int]$StartupTimeoutSeconds = 120,
    [int]$ProbeIntervalSeconds = 5
)

$ErrorActionPreference = 'Stop'

if (-not (Test-Path $JarPath)) {
    throw "Jar not found: $JarPath"
}

function Invoke-Endpoint {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Path
    )

    $url = "$BaseUrl$Path"
    try {
        $response = Invoke-WebRequest -Uri $url -UseBasicParsing -TimeoutSec 20
        $json = $null
        try {
            $json = $response.Content | ConvertFrom-Json
        } catch {
        }

        return [pscustomobject]@{
            Status  = 'OK'
            Code    = $response.StatusCode
            Length  = $response.Content.Length
            Path    = $Path
            Url     = $url
            Json    = $json
            Message = ''
        }
    } catch {
        $statusCode = if ($_.Exception.Response) { [int]$_.Exception.Response.StatusCode } else { 'ERR' }
        return [pscustomobject]@{
            Status  = 'FAIL'
            Code    = $statusCode
            Length  = 0
            Path    = $Path
            Url     = $url
            Json    = $null
            Message = $_.Exception.Message
        }
    }
}

function Wait-UntilReady {
    $deadline = (Get-Date).AddSeconds($StartupTimeoutSeconds)
    do {
        $probe = Invoke-Endpoint -Path '/v3/api-docs'
        if ($probe.Status -eq 'OK') {
            return $probe
        }
        Start-Sleep -Seconds $ProbeIntervalSeconds
    } while ((Get-Date) -lt $deadline)

    throw "Application did not become ready within $StartupTimeoutSeconds seconds."
}

function Add-Result {
    param(
        [ref]$Results,
        [Parameter(Mandatory = $true)]
        [string]$Label,
        [Parameter(Mandatory = $true)]
        [object]$Result
    )

    $Results.Value += [pscustomobject]@{
        Label   = $Label
        Status  = $Result.Status
        Code    = $Result.Code
        Length  = $Result.Length
        Path    = $Result.Path
        Message = $Result.Message
    }
}

function Add-Skip {
    param(
        [ref]$Results,
        [Parameter(Mandatory = $true)]
        [string]$Label,
        [Parameter(Mandatory = $true)]
        [string]$Path,
        [Parameter(Mandatory = $true)]
        [string]$Reason
    )

    $Results.Value += [pscustomobject]@{
        Label   = $Label
        Status  = 'SKIP'
        Code    = ''
        Length  = 0
        Path    = $Path
        Message = $Reason
    }
}

function Get-FirstValue {
    param(
        [object]$Json,
        [string[]]$CandidateFields
    )

    if ($null -eq $Json) {
        return $null
    }

    $items = @()
    if ($null -ne $Json.rows) {
        $items = @($Json.rows)
    } elseif ($null -ne $Json.data) {
        $items = @($Json.data)
    }

    if ($items.Count -eq 0) {
        return $null
    }

    $first = $items[0]
    foreach ($field in $CandidateFields) {
        if ($null -ne $first.PSObject.Properties[$field] -and $null -ne $first.$field -and "$($first.$field)".Length -gt 0) {
            return "$($first.$field)"
        }
    }

    return $null
}

$results = @()
$process = $null

try {
    Write-Host "Starting app: $JarPath"
    $process = Start-Process -FilePath 'java' -ArgumentList '-jar', $JarPath -WorkingDirectory (Split-Path $JarPath) -PassThru

    Write-Host "Waiting for /v3/api-docs readiness..."
    $ready = Wait-UntilReady
    Add-Result -Results ([ref]$results) -Label 'openapi' -Result $ready

    $staticChecks = @(
        @{ Label = 'auth-isLogin'; Path = '/auth/isLogin' },
        @{ Label = 'category-allList'; Path = '/flowerapplet/category/allList' },
        @{ Label = 'category-list'; Path = '/flowerapplet/category/list?pageNum=1&pageSize=1' },
        @{ Label = 'product-list'; Path = '/flowerapplet/product/list?pageNum=1&pageSize=1' },
        @{ Label = 'announcement-list'; Path = '/flowerapplet/announcement/list?pageNum=1&pageSize=1' },
        @{ Label = 'sku-list'; Path = '/flowerapplet/sku/list?pageNum=1&pageSize=1' },
        @{ Label = 'productDetail-list'; Path = '/flowerapplet/productDetail/list?pageNum=1&pageSize=1' },
        @{ Label = 'product-queryColor'; Path = '/flowerapplet/product/queryColor' },
        @{ Label = 'product-queryLevel'; Path = '/flowerapplet/product/queryLevel' },
        @{ Label = 'sku-queryColor'; Path = '/flowerapplet/sku/queryColor' },
        @{ Label = 'sku-queryLevel'; Path = '/flowerapplet/sku/queryLevel' }
    )

    $staticResponses = @{}
    foreach ($check in $staticChecks) {
        $result = Invoke-Endpoint -Path $check.Path
        $staticResponses[$check.Label] = $result
        Add-Result -Results ([ref]$results) -Label $check.Label -Result $result
    }

    $categoryId = Get-FirstValue -Json $staticResponses['category-allList'].Json -CandidateFields @('id', 'categoryId')
    if ($null -ne $categoryId) {
        Add-Result -Results ([ref]$results) -Label 'category-detail' -Result (Invoke-Endpoint -Path "/flowerapplet/category/$categoryId")
        Add-Result -Results ([ref]$results) -Label 'product-queryCategory' -Result (Invoke-Endpoint -Path "/flowerapplet/product/queryCategory/$categoryId/1/1")
    } else {
        Add-Skip -Results ([ref]$results) -Label 'category-detail' -Path '/flowerapplet/category/{id}' -Reason 'No category id resolved from category-allList.'
        Add-Skip -Results ([ref]$results) -Label 'product-queryCategory' -Path '/flowerapplet/product/queryCategory/{categoryId}/1/1' -Reason 'No category id resolved from category-allList.'
    }

    $productId = Get-FirstValue -Json $staticResponses['product-list'].Json -CandidateFields @('id', 'productId', 'prodId')
    if ($null -ne $productId) {
        Add-Result -Results ([ref]$results) -Label 'product-detail' -Result (Invoke-Endpoint -Path "/flowerapplet/product/$productId")
    } else {
        Add-Skip -Results ([ref]$results) -Label 'product-detail' -Path '/flowerapplet/product/{id}' -Reason 'No product id resolved from product-list.'
    }

    $announcementId = Get-FirstValue -Json $staticResponses['announcement-list'].Json -CandidateFields @('announcementId', 'id')
    if ($null -ne $announcementId) {
        Add-Result -Results ([ref]$results) -Label 'announcement-detail' -Result (Invoke-Endpoint -Path "/flowerapplet/announcement/$announcementId")
    } else {
        Add-Skip -Results ([ref]$results) -Label 'announcement-detail' -Path '/flowerapplet/announcement/{announcementId}' -Reason 'No announcement id resolved from announcement-list.'
    }

    $skuId = Get-FirstValue -Json $staticResponses['sku-list'].Json -CandidateFields @('skuId', 'id')
    if ($null -ne $skuId) {
        Add-Result -Results ([ref]$results) -Label 'sku-detail' -Result (Invoke-Endpoint -Path "/flowerapplet/sku/$skuId")
        Add-Result -Results ([ref]$results) -Label 'productDetail-bySkuId' -Result (Invoke-Endpoint -Path "/flowerapplet/productDetail/getInfoBySkuId/$skuId")
    } else {
        Add-Skip -Results ([ref]$results) -Label 'sku-detail' -Path '/flowerapplet/sku/{skuId}' -Reason 'No sku id resolved from sku-list.'
        Add-Skip -Results ([ref]$results) -Label 'productDetail-bySkuId' -Path '/flowerapplet/productDetail/getInfoBySkuId/{skuId}' -Reason 'No sku id resolved from sku-list.'
    }

    $detailId = Get-FirstValue -Json $staticResponses['productDetail-list'].Json -CandidateFields @('detailId', 'id')
    if ($null -ne $detailId) {
        Add-Result -Results ([ref]$results) -Label 'productDetail-detail' -Result (Invoke-Endpoint -Path "/flowerapplet/productDetail/$detailId")
    } else {
        Add-Skip -Results ([ref]$results) -Label 'productDetail-detail' -Path '/flowerapplet/productDetail/{detailId}' -Reason 'No detail id resolved from productDetail-list.'
    }

    $results | Format-Table -AutoSize

    if (($results | Where-Object { $_.Status -eq 'FAIL' }).Count -gt 0) {
        throw 'Smoke API verification failed.'
    }
} finally {
    if ($null -ne $process -and -not $process.HasExited) {
        Stop-Process -Id $process.Id -Force
    }
}
