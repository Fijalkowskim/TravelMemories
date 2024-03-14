import { PopupMessageType } from "./PopupMessageType";

export interface PopupMessage {
  message: string;
  type: PopupMessageType;
  id: number;
}
