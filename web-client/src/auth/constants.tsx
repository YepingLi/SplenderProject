export const authConstants = {
    duration: "duration",
    accessToken: "accessToken",
    refreshToken: "refreshToken",
    startTime: "startTime"
}

export function saveToLocal(name: string, value: any){
    localStorage.setItem(name, `${value}`)
}

export function getFromLocal(name: string): string {
    let value = localStorage.getItem(name);
    if (!value) {
        throw new Error(`No valued saved for ${name}`)
    }
    switch(name) {
        case authConstants.accessToken:
        case authConstants.refreshToken:
        case authConstants.duration:
        case authConstants.startTime:
            return value;
        default:
            throw new Error(`No such value ${name}`);
    }
}

/**
 * Get the item in local store and convert it to a number
 * @param name The name ofthe item in local storage
 * @returns 
 */
export function getNumberFromLocal(name: string) {
    let value: any = getFromLocal(name);
    switch(name) {
        case authConstants.duration:
        case authConstants.startTime:
            return Number.parseFloat(value);
        default:
            throw new Error(`Cannot convert ${name} to Number`);
    }
}