import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { UserContextProvider } from "./context/UserContext";
import { TravelsContextProvider } from "./context/TravelsContext";
import { MapContextProvider } from "./context/MapContext";
import { PopupContextProvider } from "./context/PopupContext";
import { SettingsContextProvider } from "./context/SettingsContext";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <React.StrictMode>
    <SettingsContextProvider>
      <PopupContextProvider>
        <TravelsContextProvider>
          <UserContextProvider>
            <MapContextProvider>
              <App />
            </MapContextProvider>
          </UserContextProvider>
        </TravelsContextProvider>
      </PopupContextProvider>
    </SettingsContextProvider>
  </React.StrictMode>
);
