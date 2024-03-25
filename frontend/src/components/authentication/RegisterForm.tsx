import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { FaEye } from "react-icons/fa";
import FormInput from "./FormInput";

type Props = {
  onSubmit: (username: string, password: string) => void;
};

function RegisterForm({ onSubmit }: Props) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [retypePassword, setRetypePassword] = useState("");
  const [showRetypePassword, setShowRetypePassword] = useState(false);
  return (
    <form
      className="flex flex-col gap-4 justify-center items-center py-6 w-full max-w-[20rem] bg-primary-50 p-10 rounded-lg shadow-md"
      onSubmit={(e) => {
        e.preventDefault();
        if (email === "" || password === "") {
          return;
        }
        onSubmit(email, password);
      }}
    >
      <div className="relative w-full">
        <FormInput id="email" value={email} setValue={setEmail} required type="text" placeholder="user@email.com">Email</FormInput>
      </div>
      <div className="relative w-full">
      <FormInput id="password" value={password} setValue={setPassword} required type={showPassword ? "text" : "password"}>Password</FormInput>
        <button
          className="absolute bottom-0 h-10 right-3 z-10"
          type="button"
          onClick={() => {
            setShowPassword((prev) => !prev);
          }}
        >
          <FaEye />
        </button>
      </div>
      <div className="relative w-full">
      <FormInput id="retypePassword" value={retypePassword} setValue={setRetypePassword} required type={showRetypePassword ? "text" : "password"}>Retype password</FormInput>
        <button
          className="absolute bottom-0 h-10 right-3 z-10"
          type="button"
          onClick={() => {
            setShowRetypePassword((prev) => !prev);
          }}
        >
          <FaEye />
        </button>
      </div>
      <div className="w-full">
        <button
          className="text-sm font-medium mt-2 h-10 w-full bg-action-400 hover:bg-action-500 text-background-50 p-2 rounded-md transition-colors"
          type="submit"
        >
          Register
        </button>
        <div className="mt-4 text-center text-sm text-background-700">
        Already have an account?{" "}
        <button className="underline" onClick={() => {}}>
          <NavLink to={"/login"}>Sign in</NavLink>
        </button>
      </div>
      </div>
    </form>
  );
}

export default RegisterForm;
