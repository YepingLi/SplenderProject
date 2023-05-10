import { Meta } from "./game";

export type UrlProducer = (name: number) => string;
export type CardUrlProducer = (name: Meta) => string;