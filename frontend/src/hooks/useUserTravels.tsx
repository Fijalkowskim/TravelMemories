import { useCallback, useEffect, useState } from "react";
import { UserData } from "../models/UserData";
import { useUserContext } from "../context/UserContext";
import { useTravelsContext } from "../context/TravelsContext";
import { TravelData } from "../models/travel/TravelData";

export const useUserTravels = (
  page?: number,
  pageSize?: number,
  sort?: string
) => {
  const { userData } = useUserContext();
  const { LoadUserTravels } = useTravelsContext();
  const [travels, setTravels] = useState<TravelData[]>([]);
  const [totalPages, setTotalPages] = useState<number>(1);

  const fetchTravels = useCallback(
    async (userData: UserData) => {
      const travelsPageData = await LoadUserTravels(
        userData,
        page,
        pageSize,
        sort
      );
      setTravels(travelsPageData.travels);
      setTotalPages(travelsPageData.totalPages);
    },
    [LoadUserTravels, page, pageSize, sort]
  );
  useEffect(() => {
    if (userData) {
      fetchTravels(userData);
    }
  }, [fetchTravels, userData]);
  return { travels, totalPages };
};
