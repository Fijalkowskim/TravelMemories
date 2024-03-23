import { ReactNode, createContext, useContext, useState } from "react";

interface SettingsContextProviderProps {
  children: ReactNode;
}
interface SettingsContextProps {
  disableScroll: boolean;
  setDisableScroll: React.Dispatch<React.SetStateAction<boolean>>;
  apiConnected: boolean;
  firstLoading: boolean;
  setFirstLoading: React.Dispatch<React.SetStateAction<boolean>>;
}
const SettingsContext = createContext({} as SettingsContextProps);

export function useSettingsContext() {
  return useContext(SettingsContext);
}

export function SettingsContextProvider({
  children,
}: SettingsContextProviderProps) {
  const [disableScroll, setDisableScroll] = useState(false);
  const [apiConnected] = useState(false);
  const [firstLoading, setFirstLoading] = useState(true);
  return (
    <SettingsContext.Provider
      value={{
        disableScroll,
        setDisableScroll,
        apiConnected,
        firstLoading,
        setFirstLoading,
      }}
    >
      {children}
    </SettingsContext.Provider>
  );
}
