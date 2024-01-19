$ApplicationId = "qrpayments"
$Tags = @{
    Provisioner = "PowerShell"
    Environment = "Development"
    "Technical-Owner" = "TeamDragons"
    "Application-Id" = $ApplicationId
    "Data-Classification" = "Restricted"
}

$ResourceGroupName = "TeamDragons_rg"
$ResourceGroup = Get-AzResourceGroup -Name $ResourceGroupName -ErrorAction Stop
$Location = $ResourceGroup.Location

# Azure resources to be created
$SqlServerName = $ApplicationId + "sqlserver"
$SqlDatabaseName = $ApplicationId + "database"
$AppServicePlanName = $ApplicationId + "serviceplan"
$WebAppName = $ApplicationId + "webapp"

$AdminUser = "azuser"
$AdminPassSecure = "your@p4ssw0rd" | ConvertTo-SecureString -AsPlainText -Force
$Credentials = New-Object System.Management.Automation.PSCredential ($AdminUser, $AdminPassSecure)

$CommonProps = @{
    ResourceGroupName = $ResourceGroup.ResourceGroupName
    Location = $Location
    ServerName = $SqlServerName
    ServerVersion = "12.0"
    SqlAdministratorCredentials = $Credentials
    Tags = $Tags
}

Write-Host "1. Get or Create new SQL Server"
$SqlServer = Get-AzSqlServer -ResourceGroupName $ResourceGroupName -ServerName $SqlServerName -ErrorAction SilentlyContinue
if ($null -eq $SqlServer) {
    Write-Host "Creating new SQL Server.."
    $SqlServer = New-AzSqlServer @CommonProps
}

Write-Host "SQL Server: $($SqlServer.ServerName)"
Write-Host "SQL Server FQDN: $($SqlServer.FullyQualifiedDomainName)"

$CommonProps = @{
    ResourceGroupName = $ResourceGroup.ResourceGroupName
    DatabaseName  = $SqlDatabaseName
    ServerName = $SqlServer.ServerName
    Edition = "GeneralPurpose" # Free, Basic, Standard, Premiun, GeneralPurpose
    ComputeModel = "Provisioned" # Serverless
    VCore = 2
    ComputeGeneration = "Gen5"
    Tags = $Tags
}

Write-Host "2. Get or Create new SQL Database"
$SqlDatabase = Get-AzSqlDatabase -ResourceGroupName $ResourceGroupName -ServerName $SqlDatabaseName -ErrorAction SilentlyContinue
if ($null -eq $SqlDatabase) {
    Write-Host "Creating new SQL Database.."
    $SqlDatabase = New-AzSqlDatabase @CommonProps
}

Write-Host "SQL Database: $($SqlDatabase.DatabaseName)"

$CommonPropsList = @(
    @{
        ResourceGroupName = $ResourceGroup.ResourceGroupName
        ServerName = $SqlServer.ServerName
        FirewallRuleName = "Allow-AllAzureServices"
        StartIpAddress = "0.0.0.0"
        EndIpAddress = "0.0.0.0"
    }
)

Write-Host "3. Get or Create new SQL Server Firewall rules"
foreach ($CommonProps in $CommonPropsList) {
    $SqlServerFirewallRule = Get-AzSqlServerFirewallRule -ResourceGroupName $ResourceGroupName -ServerName $SqlServerName -FirewallRuleName $CommonProps.FirewallRuleName -ErrorAction SilentlyContinue
    if ($null -eq $SqlServerFirewallRule) {
        Write-Host "Creating new SQL Server.."
        $SqlServerFirewallRule = New-AzSqlServerFirewallRule @CommonProps
    }
    Write-Host "Firewall Rule: $($SqlServerFirewallRule.FirewallRuleName)"
}

$CommonProps = @{
    ResourceGroupName = $ResourceGroup.ResourceGroupName
    Location = $Location
    Name = $AppServicePlanName
    Tier = "Standard" # Free, Basic, Standard, Premium
    NumberofWorkers = 2
    WorkerSize = "Medium" # Small, Medium, Large
    Linux = $true
    Tag = $Tags
}

Write-Host "4. Get or Create new App Service Plan"
$AppServicePlan = Get-AzAppServicePlan -ResourceGroupName $ResourceGroupName -Name $AppServicePlanName -ErrorAction SilentlyContinue
if ($null -eq $AppServicePlan) {
    Write-Host "Creating new App Service Plan.."
    $AppServicePlan = New-AzAppServicePlan @CommonProps
}

Write-Host "App Service Plan: $($AppServicePlan.Name)"

$CommonProps = @{
    ResourceGroupName = $ResourceGroup.ResourceGroupName
    Location = $Location
    Name = $AppServicePlanName
    Tier = "Standard" # Free, Basic, Standard, Premium
    NumberofWorkers = 2
    WorkerSize = "Small" # Small, Medium, Large
    Linux = $true
    Tag = $Tags
}

Write-Host "1. Get or Create new App Service Plan"
$AppServicePlan = Get-AzAppServicePlan -ResourceGroupName $ResourceGroupName -Name $AppServicePlanName -ErrorAction SilentlyContinue
if ($null -eq $AppServicePlan) {
    Write-Host "Creating new App Service Plan.."
    $AppServicePlan = New-AzAppServicePlan @CommonProps
}
Write-Host "App Service Plan: $($AppServicePlan.Name)"

$CommonWebAppProps = @{
    ResourceGroupName = $ResourceGroup.ResourceGroupName
    Location = $Location
    Name = $WebAppName
    AppServicePlan = $AppServicePlan.Id
    ContainerImageName = "psharpx/productsmanagement-api:latest"
    Tag = $Tags
}

Write-Host "5. Get or Create new Web App"
$WebApp = Get-AzWebApp -ResourceGroupName $ResourceGroupName -Name $WebAppName -ErrorAction SilentlyContinue
if ($null -eq $WebApp) {
    Write-Host "Creating new Web App.."
    $WebApp = New-AzWebApp @CommonWebAppProps
}
Write-Host "Web App: $($WebApp.Name)"
Write-Host "Web App Hostname: $($WebApp.DefaultHostName)"


Write-Host "6. Update new Web App"

$AppSettings = @{}
foreach ($Item in $WebApp.SiteConfig.AppSettings) {
    $AppSettings[$Item.Name] = $Item.Value
}

# Configure Spring Boot application using environment variables
$AppSettings["SPRING_PROFILES_ACTIVE"] = "dev"
$AppSettings["SERVER_PORT"] = "80"
$AppSettings["DATABASE_HOSTNAME"] = "qrpaymentssqlserver.database.windows.net"
$AppSettings["DATABASE_PORT"] = "1433"
$AppSettings["DATABASE_NAME"] = "qrpaymentsdatabase"
$AppSettings["SPRING_DATASOURCE_USERNAME"] = "azuser"
$AppSettings["SPRING_DATASOURCE_PASSWORD"] = "your@p4ssw0rd"
$AppSettings["SPRING_DATASOURCE_DRIVERCLASSNAME"] = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
$AppSettings["SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT"] = "org.hibernate.dialect.SQLServer2012Dialect"
$AppSettings["SPRING_DATASOURCE_URL"] = "jdbc:sqlserver://qrpaymentssqlserver.database.windows.net:1433;databaseName=qrpaymentsdatabase"

$ContainerImageName = "psharpx/productsmanagement-api:latest"

$CommonWebAppProps = @{
    ResourceGroupName = $ResourceGroup.ResourceGroupName
    Name = $WebAppName
    AppServicePlan = $AppServicePlan.Id
    ContainerImageName = $ContainerImageName
    AppSettings = $AppSettings
    HttpLoggingEnabled = $true
    HttpsOnly = $true
}

Write-Host "Web App: $($WebApp.Name)"
Set-AzWebApp @CommonWebAppProps
