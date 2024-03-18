import React from 'react'
import { TravelsPageData } from '../../models/travel-part/travel/TravelsPageData'
import { TravelPartData } from '../../models/travel-part/TravelPartData'
import TravelPartCard from './TravelPartCard';

type Props = {
  data: TravelPartData[];
}

function TravelPartDisplay({data}: Props) {
  return (
    <ul className='flex flex-row flex-wrap items-start justify-between w-full mx-auto gap-3'>{data.map(entry=><TravelPartCard key={entry.id} data={entry}/>)}</ul>
  )
}

export default TravelPartDisplay