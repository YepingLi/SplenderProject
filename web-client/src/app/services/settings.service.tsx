import { environment } from "../../environment/environment";
import { MinimalGameService } from "../models/gameService";
import { ISession, sessionFactory } from "../models/sessions";
import { BaseService } from "./base.service";

export class SettingsService extends BaseService {

    constructor() {
        super(environment.lobbyService.getApiUrl());
    }

    public  changePsw(oldPsw : string, newPsw : string) {
        return this.post<string>(environment.lobbyService.api.users,{
            data:{
                nextPassword: newPsw,
                oldPassword : oldPsw
            }

        })
    }


}
export default SettingsService;