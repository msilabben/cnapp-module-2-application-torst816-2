val copyleftLicenses = licenseClassifications.licensesByCategory["copyleft"].orEmpty()

fun PackageRule.howToFixDefault() = """
    Review whether this dependency is allowed.

    If it is approved, document the approval with an ORT resolution.
    If it is not approved, remove or replace the dependency.
""".trimIndent()

fun PackageRule.LicenseRule.isCopyleft() =
    object : RuleMatcher {
        override val description = "isCopyleft($license)"

        override fun matches() = license in copyleftLicenses
    }

fun RuleSet.gplOrAgplInDependencyRule() = dependencyRule("GPL_OR_AGPL_IN_DEPENDENCY") {
    licenseRule("GPL_OR_AGPL_IN_DEPENDENCY", LicenseView.CONCLUDED_OR_DECLARED_OR_DETECTED) {
        require {
            +isCopyleft()
        }

        issue(
            Severity.ERROR,
            "The project ${project.id.toCoordinates()} has a dependency licensed under copyleft license $license.",
            "Review whether this dependency is allowed. If it is not approved, remove or replace the dependency."
        )
    }
}

val ruleSet = ruleSet(ortResult, licenseInfoResolver, resolutionProvider) {
    gplOrAgplInDependencyRule()
}

ruleViolations += ruleSet.violations