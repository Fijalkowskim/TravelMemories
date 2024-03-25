import React from "react";
import { useUserContext } from "../../context/UserContext";
import LogoHomeNavlink from "../../components/authentication/LogoHomeNavlink";
import RegisterForm from "../../components/authentication/RegisterForm";
import { usePopupContext } from "../../context/PopupContext";
import { PopupMessageType } from "../../models/popups/PopupMessageType";
function RegisterPage() {
  const { Register } = useUserContext();
  const {addMessage} = usePopupContext();

  const onFormSubmit = async (username: string, password: string, retypePassword: string) => {
    if(password !== retypePassword){
      addMessage("Passwords must match", PopupMessageType.ERROR, 3000);
      return;
    }
    const res = await Register({ email: username, password: password });
    if (res) {
    }
  };
  return (
    <div className="flex flex-col min-h-[100vh] justify-center items-center bg-gradient-to-br from-background-100 to-background-200 via-background-200 gap-4">
      <LogoHomeNavlink />
      <RegisterForm onSubmit={onFormSubmit} />
    </div>
  );
}


export default RegisterPage;
