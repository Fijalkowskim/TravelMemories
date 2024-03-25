import React, {
  ReactNode,
  createContext,
  useContext,
  useState,
  useEffect,
} from "react";
import { UserData } from "../models/user/UserData";
import { CookiesProvider, useCookies } from "react-cookie";
import { IoMdReturnLeft } from "react-icons/io";
import { useTravelsContext } from "./TravelsContext";
import api from "../api/api";
import { AuthenticationRequest } from "../models/user/AuthenticationRequest";
import { usePopupContext } from "./PopupContext";
import { PopupMessageType } from "../models/popups/PopupMessageType";

interface UserContextProviderProps {
  children: ReactNode;
}
interface UserContextProps {
  LogIn: (
    authenticationRequest: AuthenticationRequest
  ) => Promise<UserData | undefined>;
  Register: (
    authenticationRequest: AuthenticationRequest
  ) => Promise<UserData | undefined>;
  LogOut: () => void;
  DeleteAccount: (password: string) => Promise<boolean>;

  isLoggedIn: boolean;
  userData: UserData | undefined;
  GetUserData: () => UserData | undefined;
  LoginFromCookies: () => UserData | undefined;
}
const UserContext = createContext({} as UserContextProps);

export function useUserContext() {
  return useContext(UserContext);
}

export function UserContextProvider({ children }: UserContextProviderProps) {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userData, setUserData] = useState<UserData | undefined>(undefined);
  const [userCookie, setUserCookie, removeUserCookie] = useCookies(["user"]);

  const { addMessage } = usePopupContext();
  const AuthenticationApiCall = async (
    authenticationRequest: AuthenticationRequest,
    authenticationType: "login" | "register"
  ): Promise<UserData | undefined> => {
    let userData: UserData | undefined;
    try {
      const res = await api.post(
        `/public/authenticate/${authenticationType}?email=${authenticationRequest.email}&password=${authenticationRequest.password}`
      );

      if (res && res.data) {
        userData = {
          email: res.data.email,
          id: res.data.id,
          role: res.data.role,
          token: res.data.token,
        };
      }
    } catch (err) {
      addMessage("Wrong email/password", PopupMessageType.ERROR, 3000);
    }
    return userData;
  };
  const LogIn = async (
    authenticationRequest: AuthenticationRequest
  ): Promise<UserData | undefined> => {
    const userData = await AuthenticationApiCall(
      authenticationRequest,
      "login"
    );
    if (userData) {
      setUserData(userData);
      setIsLoggedIn(true);
      setUserCookie("user", userData, { path: "/" });
    } else {
    }
    return userData;
  };
  const Register = async (
    authenticationRequest: AuthenticationRequest
  ): Promise<UserData | undefined> => {
    const userData = await AuthenticationApiCall(
      authenticationRequest,
      "register"
    );
    if (userData) {
      setUserData(userData);
      setIsLoggedIn(true);
      setUserCookie("user", userData, { path: "/" });
    } else {
    }
    return userData;
  };
  const LogOut = () => {
    setUserData(undefined);
    removeUserCookie("user", { path: "/" });
    setIsLoggedIn(false);
  };
  const LoginFromCookies = () => {
    const storedUserData = userCookie["user"] as UserData;
    if (storedUserData) {
      setUserData(storedUserData);
      setIsLoggedIn(true);
      return storedUserData;
    }
    return undefined;
  };
  const DeleteAccount = async (password: string): Promise<boolean> => {
    if (!userData) {
      return false;
    }
    try {
      api.delete(`/user?email=${userData.email}&password=${password}`);
      LogOut();
      return true;
    } catch (err) {
      console.log(err);
    }
    return false;
  };
  const GetUserData = () => {
    return userData;
  };

  return (
    <CookiesProvider>
      <UserContext.Provider
        value={{
          isLoggedIn,
          LogIn,
          LogOut,
          userData,
          GetUserData,
          LoginFromCookies,
          DeleteAccount,
          Register,
        }}
      >
        {children}
      </UserContext.Provider>
    </CookiesProvider>
  );
}
