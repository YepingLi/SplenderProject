import { environment } from "../../environment/environment";
import { HttpClient } from "../../shared/httpClient";
import { service } from "./service";

export class SettingsService {
    private client: HttpClient;
    private baseUrl = environment.lobbyService.getApiUrl();
    constructor() {
        this.client = service.client;
    }


    private urlProducer(endpoint: string) {
        return `${this.baseUrl}${endpoint}`
    }

    public changePsw(oldPsw: string, newPsw: string, userN: string) {
        return this.client.post<string>(this.urlProducer(`${environment.lobbyService.api.users}/${userN}/password`), {
            data: {
                'nextPassword': newPsw,
                'oldPassword': oldPsw
            }
        }
        )
    }

    public changeColour(colour: any, userN: string) {
        let theColour = colour.toString().toUpperCase()
        return this.client.post<string>(this.urlProducer(`${environment.lobbyService.api.users}/${userN}/colour`), {
            data: { colour: theColour }
        })
    }
}
export default SettingsService;