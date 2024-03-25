import React from "react";
import ContentDisplay from "../../components/content-display/ContentDisplay";
import { useUserTravels } from "../../hooks/useUserTravels";
import PageWrapper from "../PageWrapper";
import TravelPartDisplay from "../../components/travel-part-display/TravelPartDisplay";
import LoadingSpinner from "../../components/general/LoadingSpinner";

function YourTravelsPage() {
  // const { travels } = useUserTravels();
  return (
    <PageWrapper alignColStart>
      <div className="w-full h-full">
        {/* <TravelPartDisplay data={travels}/> */}{" "}
        <LoadingSpinner className="h-[90vh] text-2xl" />
      </div>
    </PageWrapper>
  );
}

export default YourTravelsPage;
