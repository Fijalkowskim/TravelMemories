import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { UserContextProvider } from "./context/UserContext";
import { TravelsContextProvider } from "./context/TravelsContext";
import { MapContextProvider } from "./context/MapContext";
import { PopupContextProvider } from "./context/PopupContext";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <React.StrictMode>
    <PopupContextProvider>
      <TravelsContextProvider>
        <UserContextProvider>
          <MapContextProvider>
            <App />
          </MapContextProvider>
        </UserContextProvider>
      </TravelsContextProvider>
    </PopupContextProvider>
  </React.StrictMode>
);
