import React from "react";
import { useUserContext } from "../../context/UserContext";
import LoginForm from "../../components/authentication/LoginForm";
import LogoHomeNavlink from "../../components/authentication/LogoHomeNavlink";

function LoginPage() {
  const { LogIn } = useUserContext();

  const onFormSubmit = async (username: string, password: string) => {
    const res = await LogIn({ email: username, password: password });
    if (res) {
    }
  };
  return (
    <div className="flex flex-col min-h-[100vh] justify-center items-center bg-gradient-to-br from-background-100 to-background-200 via-background-200 gap-4">
      <LogoHomeNavlink />
      <LoginForm onSubmit={onFormSubmit} />
    </div>
  );
}

export default LoginPage;
