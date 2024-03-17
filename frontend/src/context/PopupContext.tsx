import { ReactNode, createContext, useContext, useState } from "react";
import { PopupMessage } from "../models/popups/PopupMessage";
import { PopupMessageType } from "../models/popups/PopupMessageType";
import { v4 as uuidv4 } from "uuid";
interface PopupContextProviderProps {
  children: ReactNode;
}
interface PopupContextProps {
  messages: PopupMessage[];
  clearMessages: () => void;
  addMessage: (
    message: string,
    type: PopupMessageType,
    timeout?: number
  ) => void;
  removeMessage: (id: number) => void;
}
const PopupContext = createContext({} as PopupContextProps);

export function usePopupContext() {
  return useContext(PopupContext);
}

export function PopupContextProvider({ children }: PopupContextProviderProps) {
  const [messages, setMessages] = useState<PopupMessage[]>([]);
  const clearMessages = () => {
    setMessages([]);
  };

  const addMessage = (
    message: string,
    type: PopupMessageType,
    timeout?: number
  ) => {
    const newId = uuidv4();
    setMessages((prev) => [
      ...prev,
      { message: message, type: type, id: newId },
    ]);
    if (timeout) {
      if (timeout <= 0) timeout = 3000;
      setTimeout(() => {
        setMessages((prev) => prev.filter((mess) => mess.id !== newId));
      }, timeout);
    }
  };
  const removeMessage = (id: number) => {
    setMessages((prev) => prev.filter((m) => m.id !== id));
  };
  return (
    <PopupContext.Provider
      value={{
        messages,
        clearMessages,
        addMessage,
        removeMessage,
      }}
    >
      {children}
    </PopupContext.Provider>
  );
}
