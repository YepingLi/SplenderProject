import { environment } from "../../../environment/environment"
import { Meta } from "../../models/game";


/**
 * Build the call to the server where the items are stored (no more hard coding)
 * @param service 
 * @param type 
 * @param ending 
 * @returns 
 */
function getAssetURL(service: string, type: string, ending: Record<string, any>) {
    let strEnding = Object.keys(ending).map((key) => `${key}=${ending[key]}`).join("&");
    let url = new URL(service);
    return `${url.origin}${environment.gameAssets}/${type}?${strEnding}`;
}

const cardTypeMapping: Record<string, string> = {
    "DEVELOPMENT": "Card",
    "ORIENT": "Orient"
}

export const getCard = (service: string, cardMeta: Meta) => {
    return getAssetURL(service, cardTypeMapping[cardMeta.type!], {id: cardMeta.id, level: cardMeta.level});
};

export const getNoble = (service: string, nobleId: number) => getAssetURL(service, "Noble", {id: nobleId});

export const getToken = (service: string, tokenId: string) => getAssetURL(service, "Token", {id: tokenId});

export const getTradingPost = (service: string, cardId: number) => getAssetURL(service, "TradingPost", {id: cardId});

export const getCities = (service: string, cardId: number) => getAssetURL(service, "City", {id: cardId});

export const getBackground = (service: string ) => getAssetURL(service, "Background", {id: 1, level: 1});