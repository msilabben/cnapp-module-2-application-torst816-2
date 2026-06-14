from collections.abc import Generator

import pytest
from fastapi import FastAPI
from starlette.testclient import TestClient

from app.main import create_app


@pytest.fixture(scope="session", autouse=True)
def startup() -> Generator[None, None, None]:
    print("Starting tests!")

    yield

    print("Tests done!")


@pytest.fixture(scope="session")
def app() -> FastAPI:
    return create_app()


@pytest.fixture(scope="session")
def client(app: FastAPI) -> Generator[TestClient, None, None]:
    with TestClient(app) as test_client:
        yield test_client