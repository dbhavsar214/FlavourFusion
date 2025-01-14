'use client'
import Image from 'next/image';
import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { BASE_URL } from '@/config/apiConfig'; // Import the base URL

const TRecipe = () => {

  interface Card {
    id: number,
    title: string,
    description: string,
    imageURL: string,
    rating: string,
    ingredients: string[];
    readonly name: string;
    readonly recipeID: number;
  }

  const Router = useRouter();
  const [cardsData, setCardsData] = useState<Card[]>([]);
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch data from your backend API
        const response = await fetch(`${BASE_URL}/recipes/all`);
        const data = await response.json();
        console.log("data", data);
        setCardsData(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchData();
  }, []);

  const handlePrev = () => {
    setCurrentIndex((prevIndex) => (prevIndex === 0 ? cardsData.length - 1 : prevIndex - 1));
  };

  const handleNext = () => {
    setCurrentIndex((prevIndex) => (prevIndex === cardsData.length - 1 ? 0 : prevIndex + 1));
  };

  const handleButtonClick = (id: number) => {
    console.log('Button clicked for recipe ID:', id);
    Router.push(`/recipe/${id}`);
  };

  return (
    <div className="bg-gray-100 min-h-screen flex flex-col justify-center items-center p-4">
      <div className="flex justify-center mb-8">
        <h1 className="text-4xl font-bold text-teal-600">TRENDING RECIPES</h1>
      </div>
      <div className="relative w-full max-w-6xl">
        <div className="overflow-hidden">
          <div className="flex transition-transform duration-500" style={{ transform: `translateX(-${currentIndex * 100}%)` }}>
            {cardsData.map((card, index) => (
              <div key={index} className="w-full flex-shrink-0 flex px-6">
                <div className="flex relative bg-white rounded-lg shadow-lg overflow-hidden md:flex-row flex-col">
                  <div className='h-1/2 w:1/2 sm:h-full sm:w-full'>
                    <Image src="https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" width={500} height={500} alt={card.name}></Image>
                  </div>
                  <div className="w-full md:w-1/2 p-6">
                    <div>
                      <h2 className="text-2xl font-bold mb-4">{card.name}</h2>
                    </div>
                    <div className="text-yellow-500 mb-4">{card.rating}</div>
                    <div className='flex flex-col sm:flex-row'>
                      <h2 className="text-2xl font-bold mb-4 mr-1">Ingredients</h2>
                      <select className='bg-teal-600 text-white p-1 rounded-md mb-4 w-max'>
                        {card.ingredients.map((ingredient, idx) => (
                          <option key={idx} value={ingredient} className='bg-white text-teal-600'>{ingredient}</option>
                        ))}
                      </select>
                    </div>
                    <div>
                      <p className="text-gray-700 w-fit">{card.description}</p>
                    </div>
                    <button
                      className="mt-4 bg-teal-600 text-white p-2 rounded-md"
                      onClick={() => handleButtonClick(card.recipeID)}
                    >
                      View Recipe
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
        <button
          className="absolute left-2.5 top-1/2 transform -translate-y-1/2 bg-teal-600 text-white p-2 rounded-full"
          onClick={handlePrev}
        >
          &lt;
        </button>
        <button
          className="absolute right-2.5 top-1/2 transform -translate-y-1/2 bg-teal-600 text-white p-2 rounded-full"
          onClick={handleNext}
        >
          &gt;
        </button>
      </div>
    </div>
  );
};

export default TRecipe;
