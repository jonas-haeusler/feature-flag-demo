# Demo project showcasing OpenFeature Java SDK, OpenFeature Operator and flagd

This is based on https://openfeature.dev/docs/tutorials/open-feature-operator/quick-start

## Setup

Install `kubectl`, `kind` and `just`.

1. Use `just setup-kind` to create a local k8s cluster with cert-manager
2. Use `just setup-openfeature-operator` to install the OpenFeature Operator
3. Use `just release` to build, publish and deploy the application
4. Finally, use `just destroy-kind` to completely remove the local k8s cluster 