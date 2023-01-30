
export interface MinimalToastInformation {
    msg: string
    source: string
}

export default interface Toast extends MinimalToastInformation {
    id: number
}

