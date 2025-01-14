'use client'
import React, { useState, useEffect } from 'react'
import RecipeInfo from '../../Components/RecipeInfo'
import RecipeComments from '../../Components/RecipeComments'
import { useParams } from 'next/navigation'
import { BASE_URL } from '@/config/apiConfig'; // Import the base URL

const Page = () => {
  const params = useParams()
  const [recipes, setRecipes] = useState([])
  const [comments, setComments] = useState([])
  const recipeID: any = params.recipeId

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch data from your backend API
        const response = await fetch(`${BASE_URL}/recipes/${recipeID}`);
        const data = await response.json();

        setRecipes(data);

        console.log("data", data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchData();

    const fetchcomments = async () => {
      try {
        // Fetch data from your backend API
        console.log('comment function called');

        const response = await fetch(`${BASE_URL}/reviews/get/${recipeID}`);
        const data = await response.json();

        console.log("comments", data);

        setComments(data);
        // setComments(commentData);

        console.log("data", data);
        // console.log("commentData", commentData);

        // setCardsData(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchcomments();
  }, []);

  return (
    <div>
      <RecipeInfo recipes={recipes} />
      <RecipeComments comments={comments} />
    </div>
  )
}

export default Page
