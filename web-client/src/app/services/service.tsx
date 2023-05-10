import { TokenObserverInjector } from "../../shared/http/token-observer-injector";
import { HttpClient } from "../../shared/httpClient";

export const service = {client: new HttpClient([new TokenObserverInjector()])} 