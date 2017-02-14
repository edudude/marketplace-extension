package org.exoplatform.addon.service;


public class Addon implements Comparable<Addon>{

    private String id;

    private String version;

    private boolean unstable;

    private String name;

    private String description;

    private String releaseDate;

    private String sourceUrl;

    private String  screenshotUrl;

    private String documentationUrl;

    private String downloadUrl;

    private String vendor;

    private String author;

    private String authorEmail;

    private String license;

    private String licenseUrl;

    private boolean mustAcceptLicense;

    private String supportedDistributions;

    private String supportedApplicationServers;

    private String compatibility;

    private boolean isInstalled ;

    private boolean isCompatible ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isUnstable() {
        return unstable;
    }

    public void setUnstable(boolean unstable) {
        this.unstable = unstable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getScreenshotUrl() {
        return screenshotUrl;
    }

    public void setScreenshotUrl(String screenshotUrl) {
        this.screenshotUrl = screenshotUrl;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public boolean isMustAcceptLicense() {
        return mustAcceptLicense;
    }

    public void setMustAcceptLicense(boolean mustAcceptLicense) {
        this.mustAcceptLicense = mustAcceptLicense;
    }

    public String getSupportedDistributions() {
        return supportedDistributions;
    }

    public void setSupportedDistributions(String supportedDistributions) {
        this.supportedDistributions = supportedDistributions;
    }

    public String getSupportedApplicationServers() {
        return supportedApplicationServers;
    }

    public void setSupportedApplicationServers(String supportedApplicationServers) {
        this.supportedApplicationServers = supportedApplicationServers;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public void setInstalled(boolean installed) {
        isInstalled = installed;
    }

    public boolean isCompatible() {
        return isCompatible;
    }

    public void setCompatible(boolean compatible) {
        isCompatible = compatible;
    }

    @Override
    public int compareTo(Addon addon) {
        return getVersion().compareTo(addon.getVersion());
    }
}
