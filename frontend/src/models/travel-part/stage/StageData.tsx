import { PhotoData } from "../photo/PhotoData";
import { TravelData } from "../travel/TravelData";

export interface StageData {
  id: number | undefined;
  location: string | undefined;
  lat: number;
  lng: number;
  description: string;
  date: Date;
  photos: PhotoData[];
  parentTravel: TravelData | undefined;
}
