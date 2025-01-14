import React from 'react'
import TRecipe from '../app/Components/TRecipe'
import Categories from '../app/Components/Categories'
import UserRecommendation from '../app/Components/UserRecommendation'


const page = () => {
    return (
        <div>
            <TRecipe />
            <Categories />
            <UserRecommendation />
        </div>
    )
}

export default page


