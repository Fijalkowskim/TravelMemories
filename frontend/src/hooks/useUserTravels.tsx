import { useCallback, useEffect, useState } from "react";
import { UserData } from "../models/user/UserData";
import { useUserContext } from "../context/UserContext";
import { useTravelsContext } from "../context/TravelsContext";
import { TravelData } from "../models/travel-part/travel/TravelData";
import { usePopupContext } from "../context/PopupContext";
import { PopupMessageType } from "../models/popups/PopupMessageType";

export const useUserTravels = (
  page?: number,
  pageSize?: number,
  sort?: string
) => {
  const { userData } = useUserContext();
  const { LoadUserTravels } = useTravelsContext();
  const { addMessage } = usePopupContext();
  const [travels, setTravels] = useState<TravelData[]>([]);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const fetchTravels = useCallback(
    async (userData: UserData) => {
      setIsLoading(true);
      try {
        const travelsPageData = await LoadUserTravels(
          userData,
          page,
          pageSize,
          sort
        );
        setIsLoading(false);
        setTravels(travelsPageData.travels);
        setTotalPages(travelsPageData.totalPages);
      } catch (err) {
        addMessage("Couldn't load travels", PopupMessageType.ERROR, 2000);
        setIsLoading(false);
      }
    },
    [LoadUserTravels, page, pageSize, sort, setIsLoading, addMessage]
  );
  useEffect(() => {
    if (userData) {
      fetchTravels(userData);
    }
  }, [fetchTravels, userData]);
  return { travels, totalPages, isLoading };
};
