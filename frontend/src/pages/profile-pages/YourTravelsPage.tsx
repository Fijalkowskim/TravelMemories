import React from "react";
import ContentDisplay from "../../components/content-display/ContentDisplay";
import { useUserTravels } from "../../hooks/useUserTravels";
import PageWrapper from "../PageWrapper";
import TravelPartDisplay from "../../components/travel-part-display/TravelPartDisplay";

function YourTravelsPage() {
  const { travels } = useUserTravels();
  return (
    <PageWrapper alignColStart>
      <div className="w-full">
        {/* <ContentDisplay travels={travels} /> */}
        <TravelPartDisplay data={travels}/>
      </div>
    </PageWrapper>
  );
}

export default YourTravelsPage;
