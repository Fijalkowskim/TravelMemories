import { TravelData } from "../models/travel/TravelData";
import { TravelResponse } from "../models/travel/TravelResponse";
import Placeholder from "../images/placeholder.png";
export const TravelResponseToTravelData = (
  travelResponse: TravelResponse
): TravelData => {
  let imageSource: string = Placeholder;
  if (travelResponse.thumbnail !== null) {
    try {
      imageSource = `data:image/jpeg;base64,${travelResponse.thumbnail}`;
    } catch (err) {
      console.log("Cannot convert image");
    }
  }
  const travelData: TravelData = {
    ...travelResponse,
    thumbnail: imageSource,
    travelDate: new Date(travelResponse.travelDate),
  };
  return travelData;
};
