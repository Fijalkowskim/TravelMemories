import React, { ReactNode } from 'react'

type Props = {
    children: ReactNode;
    value:string;
    setValue: React.Dispatch<React.SetStateAction<string>>;
    type: string;
    id: string;
    required?: boolean;
    placeholder?: string;
}

function FormInput({children,value, setValue, type, id,required,placeholder}: Props) {
  return (
    <>
     <label
    className="text-sm text-gray-700 text-left w-full -mb-1"
    htmlFor={id}
  >
    {children}
  </label>
  <input
    className="h-10 w-full bg-background-50 text-sm border border-gray-300 p-2 rounded-md focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2"
    id={id}
    required={required}
    type={type}
    value={value}
    placeholder={placeholder}
    onChange={(e) => {
      setValue(e.target.value);
    }}
  /></>
  )
}

export default FormInput