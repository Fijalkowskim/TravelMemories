import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Navbar from "./components/navbar/Navbar";
import YourMemoriesPage from "./pages/profile-pages/YourTravelsPage";
import PublicMemoriesPage from "./pages/public-pages/PublicMemoriesPage";
import ProfilePage from "./pages/profile-pages/ProfilePage";
import { useUserContext } from "./context/UserContext";
import HomePage from "./pages/home-pages/HomePage";
import NewMemoryPage from "./pages/create-pages/NewMemoryPage";
import LoginPage from "./pages/profile-pages/LoginPage";
import RegisterPage from "./pages/profile-pages/RegisterPage";
import TravelPage from "./pages/detail-pages/TravelPage";
import NewTravelPage from "./pages/create-pages/NewTravelPage";
import StagePage from "./pages/detail-pages/StagePage";
import NewStagePage from "./pages/create-pages/NewStagePage";
import MemoryPage from "./pages/detail-pages/MemoryPage";
import PopupController from "./components/popup/PopupController";

function App() {
  const { isLoggedIn } = useUserContext();
  return (
    <div className="relative">
      <PopupController />
      <BrowserRouter>
        {isLoggedIn && <Navbar />}
        <Routes>
          <Route
            path="/"
            element={isLoggedIn ? <NewMemoryPage /> : <HomePage />}
          />

          <Route path="/public-memories" element={<PublicMemoriesPage />} />
          {isLoggedIn && <Route path="/profile" element={<ProfilePage />} />}
          {isLoggedIn && <Route path="/travel/:id" element={<TravelPage />} />}
          {isLoggedIn && (
            <Route path="/stage/:travelID/:stageID" element={<StagePage />} />
          )}
          {isLoggedIn && (
            <Route
              path="/memory/:travelID/:stageID/:memoryID"
              element={<MemoryPage discover={undefined} />}
            />
          )}
          <Route
            path="/memory/:travelID/:stageID/:memoryID/discover"
            element={<MemoryPage discover={true} />}
          />
          {isLoggedIn && (
            <Route path="/new-travel" element={<NewTravelPage />} />
          )}
          {isLoggedIn && (
            <Route path="/new-stage/:travelID" element={<NewStagePage />} />
          )}
          {!isLoggedIn && <Route path="/login" element={<LoginPage />} />}
          {!isLoggedIn && <Route path="/register" element={<RegisterPage />} />}
          {isLoggedIn && (
            <Route path="/travels" element={<YourMemoriesPage />} />
          )}
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
