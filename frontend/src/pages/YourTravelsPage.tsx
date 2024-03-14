import React, { useCallback, useEffect, useState } from "react";
import HorizontalDisplay from "../components/general/HorizontalDisplay";
import { useTravelsContext } from "../context/TravelsContext";
import { useUserContext } from "../context/UserContext";
import { TravelData } from "../models/travel/TravelData";
import { UserData } from "../models/UserData";
import { useUserTravels } from "../hooks/useUserTravels";

function YourTravelsPage() {
  const { travels } = useUserTravels();
  // const { LoadUserTravels, userTravels } = useTravelsContext();
  return (
    <div className="w-full min-h-[100vh] flex flex-col px-4 lg:px-20 py-4">
      {travels.map((t) => (
        <div className="my-5">{t.description}</div>
      ))}
      <div className="mt-20 flex flex-col items-center w-full gap-4">
        <h1 className="bg-background-50 text-5xl font-thin px-3 py-1 rounded-md shadow-md">
          {/* Your total travels: <b>{userTravels.length}</b> */}
        </h1>
        <div className="w-full">
          {/* <HorizontalDisplay travels={userTravels} /> */}
        </div>
      </div>
    </div>
  );
}

export default YourTravelsPage;
