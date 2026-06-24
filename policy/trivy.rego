package main

config := {
  "max_high": 12,
  "high_threshold": 7.0,
}

high_results contains result if {
  run := input.runs[_]
  result := run.results[_]
  rule := run.tool.driver.rules[_]

  rule.id == result.ruleId
  sev := to_number(object.get(rule.properties, "security-severity", "0"))
  sev >= config.high_threshold
}

deny contains msg if {
  count(high_results) > config.max_high

  msg := sprintf(
    "Too many HIGH findings: %d (max allowed: %d)",
    [count(high_results), config.max_high],
  )
}