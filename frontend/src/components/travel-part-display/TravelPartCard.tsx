import React from 'react'
import { TravelPartData } from '../../models/travel-part/TravelPartData'
import { useTravelPart } from '../../hooks/useTravelPart'
import { NavLink } from 'react-router-dom'
import { motion } from 'framer-motion'

type Props = {
    data: TravelPartData
    onClick?: (data: TravelPartData)=>void;
}

function TravelPartCard({data,onClick}: Props) {
    const {detailsLink} = useTravelPart(data);
    const handleOnClick = (e : React.MouseEvent<HTMLAnchorElement>) =>{
        if(onClick === undefined) return;
        e.preventDefault();
        onClick(data);
    }
  return (
    <NavLink to={detailsLink} onClick={handleOnClick}>
        <motion.button className='flex-grow w-1/3 p-3 bg-background-50 aspect-square shadow-md'></motion.button>
    </NavLink>
  )
}

export default TravelPartCard