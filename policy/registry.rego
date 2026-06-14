package github.workflow

allowed_prefix := "ghcr.io/msilabben/"

deny[msg] {
  some job_name
  image := jobs[job_name].container.image

  not startswith(image, allowed_prefix)

  msg := sprintf("job %q uses disallowed container image %q; only images from %q are allowed", [
    job_name,
    image,
    allowed_prefix,
  ])
}

deny[msg] {
  some job_name
  some service_name

  image := input.jobs[job_name].services[service_name].image

  not startswith(image, allowed_prefix)

  msg := sprintf("job %q service %q uses disallowed container image %q; only images from %q are allowed", [
    job_name,
    service_name,
    image,
    allowed_prefix,
  ])
}