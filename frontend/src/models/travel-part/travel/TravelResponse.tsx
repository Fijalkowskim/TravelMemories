export interface TravelResponse {
  id: number;
  locationName: string;
  latitude: number;
  longitude: number;
  travelDate: string;
  description: string;
  thumbnail: string | null;
}
