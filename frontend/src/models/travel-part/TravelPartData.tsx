import { TravelPartType } from "./TravelPartType";

export interface TravelPartData{
    id: number;
    locationName: string;
    latitude: number;
    longitude: number;
    date: Date;
    description: string;
    thumbnail: string;
    type: TravelPartType
}