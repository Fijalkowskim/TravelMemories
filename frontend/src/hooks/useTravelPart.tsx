import React from 'react'
import { TravelPartType } from '../models/travel-part/TravelPartType';
import { TravelPartData } from '../models/travel-part/TravelPartData';

export const useTravelPart = (tavelPart:TravelPartData) => {
    const createNewLink = `/new-${tavelPart.type}`
    const detailsLink =`/${tavelPart.type}/${tavelPart.id}`
    return {createNewLink,detailsLink}
}
