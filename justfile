default:
    @just --list

build:
    mvnw clean verify

build-image:
    mvnw clean install -Dquarkus.container-image.build=true

publish-image:
    kind load docker-image feature-flag-demo

deploy:
    kubectl config use-context kind-kind
    kubectl apply -k k8s
    kubectl rollout restart deployment/feature-flag-demo-deployment

release: build-image publish-image deploy

setup-kind:
    kind create cluster --config kind.yaml
    kubectl config use-context kind-kind
    kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.13.2/cert-manager.yaml
    kubectl wait --timeout=60s --for condition=Available=True deploy --all -n 'cert-manager'

setup-openfeature-operator:
    kubectl apply -f https://github.com/open-feature/open-feature-operator/releases/download/v0.6.0/release.yaml
    kubectl wait --timeout=60s --for condition=Available=True deploy --all -n 'open-feature-operator-system'

destroy-kind:
    kind delete cluster
