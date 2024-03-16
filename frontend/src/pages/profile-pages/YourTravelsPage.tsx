import React from "react";
import HorizontalDisplay from "../../components/general/HorizontalDisplay";
import { useUserTravels } from "../../hooks/useUserTravels";
import PageWrapper from "../PageWrapper";

function YourTravelsPage() {
  const { travels } = useUserTravels();
  return (
    <PageWrapper alignColStart>
      <div className=" px-4 lg:px-20 py-4 ">
        <HorizontalDisplay travels={travels} />
      </div>
    </PageWrapper>
  );
}

export default YourTravelsPage;
