import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { SlGlobe } from "react-icons/sl";
import { motion } from "framer-motion";
import { FaEye } from "react-icons/fa";

type Props = {
  onSubmit: (username: string, password: string) => void;
};

function LoginForm({ onSubmit }: Props) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  return (
    <form
      className="flex flex-col gap-4 justify-center items-center py-6 w-full max-w-[20rem] bg-primary-50 p-10 rounded-lg shadow-md"
      onSubmit={(e) => {
        e.preventDefault();
        if (username === "" || password === "") {
          return;
        }
        onSubmit(username, password);
      }}
    >
      <div className="relative w-full">
        <label
          className="text-sm text-gray-700 text-left w-full -mb-1"
          htmlFor="username"
        >
          Username
        </label>
        <input
          className="h-10 w-full bg-background-50 text-sm border border-gray-300 p-2 rounded-md focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2"
          id="username"
          placeholder="Your username"
          required
          type="text"
          value={username}
          onChange={(e) => {
            setUsername(e.target.value);
          }}
        />
      </div>
      <div className="relative w-full">
        <label
          className="text-sm text-gray-700 text-left w-full -mb-1"
          htmlFor="password"
        >
          Password
        </label>
        <input
          className="h-10 w-full bg-background-50 text-sm border border-gray-300 p-2 rounded-md focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2"
          id="password"
          required
          type={showPassword ? "text" : "password"}
          value={password}
          onChange={(e) => {
            setPassword(e.target.value);
          }}
        />
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
      <div className="w-full">
        <button
          className="text-sm font-medium mt-2 h-10 w-full bg-action-400 hover:bg-action-500 text-background-50 p-2 rounded-md transition-colors"
          type="submit"
        >
          Login
        </button>
        <div className="mt-2 text-center text-sm text-background-700">
          Don't have an account?{" "}
          <NavLink className="underline" to={"/register"}>
            Sign up
          </NavLink>
        </div>
      </div>
    </form>
  );
}

export default LoginForm;
