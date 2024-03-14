import { StageData } from "../StageData";

export interface TravelData {
  id: number;
  locationName: string;
  latitude: number;
  longitude: number;
  travelDate: Date;
  description: string;
  thumbnail: string;
}
