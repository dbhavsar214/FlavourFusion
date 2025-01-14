import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { BASE_URL } from '@/config/apiConfig'; // Import the base URL

interface Card {
    title: string;
    description: string;
    imageURLS: string[];
    rating: string;
    ingredients: string[];
    esttime: string;
    username: string;
    direction: string;
}

type RecipeInfoProps = {
    recipes: any; // Replace `any` with the actual type of your recipes
};

const RecipeInfo: React.FC<RecipeInfoProps> = ({ recipes }) => {
    const [creatorData, setCreatorData] = useState<any>(null); // State to store creator data\
    const router = useRouter();

    
    
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(`${BASE_URL}/user/${recipes.creatorID}`,
                    { // Dummy API endpoint
                    method: 'GET',
                    headers: {
                      'Content-Type': 'application/json',
                    },
                    //body: JSON.stringify(data),
                  });
                const data = await response.json();
                setCreatorData(data); // Update state with fetched data
                console.log("Fetched creator data:", data);
            } catch (error) {
                console.error("Error fetching creator data:", error);
            }
        };

        if (recipes.creatorID) {
            fetchData(); // Call fetchData only if creatorID is available
        }
    }, [recipes.creatorID]); // useEffect dependency on recipes.creatorID

    const handleButtonClick = () => {
        router.push(`/user/${recipes.creatorID}/profile`)
      };

    const cardsData: Card[] = [
        {
            title: recipes.name,
            description: recipes.description,
            direction: recipes.direction,
            imageURLS: [
                'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
                'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
                'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'
            ],
            rating: '⭐⭐⭐⭐⭐',
            ingredients: recipes.ingredients,
            esttime: '30-40 min',
            username: creatorData ? creatorData.userName : 'Unknown', // Display username from fetched data
        }
    ];

    const [currentIndex, setCurrentIndex] = useState(0);

    const handlePrev = () => {
        setCurrentIndex((prevIndex) => (prevIndex === 0 ? cardsData.length - 1 : prevIndex - 1));
    };

    const handleNext = () => {
        setCurrentIndex((prevIndex) => (prevIndex === cardsData.length - 1 ? 0 : prevIndex + 1));
    };

    return (
        <div className="min-h-screen  justify-center items-center p-1">
            <div className="relative w-full">
                <div className="overflow-hidden">
                    <div className="flex transition-transform duration-500" style={{ transform: `translateX(-${currentIndex * 100}%)` }}>
                        {cardsData.map((card, index) => (
 
                            <div key={index} className="w-full p-5 bg-white rounded-lg shadow-lg overflow-hidden p-1">
                                <div className="w-full md:w-1/2">
                                    <div>
                                        <h2 className="text-2xl font-bold mb-4">{card.title}</h2>
                                    </div>
                                    <div className="text-yellow-500 mb-4">{card.rating}</div>
                                    <div>
                
                                        <button onClick={handleButtonClick}>
                                            <h4 className="text-2xl font-bold mb-4">{card.username}</h4>
                                        </button>
                                    </div>
                                </div>
                                <div className="flex relative  md:flex-row flex-col">
                                    <div className="flex flex-col md:flex-row">
                                        <div className="w-full md:w-1/2 relative">
                                            <img src={cardsData[currentIndex].imageURLS[0]} alt={cardsData[currentIndex].title} className="w-full h-auto md:h-full object-cover" />
                                            <div className="absolute top-0 bottom-0 left-0 right-0 flex justify-center items-center">
                                                
                                            </div>
                                        </div>
                                        <div className="w-full md:w-1/2 p-5 font-semibold">
                                            <p>{cardsData[currentIndex].description}</p>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <h1 className='text-5xl font-bold py-4 text-teal-600'>Ingredients</h1>
                                    <div className='grid grid-cols-4 gap-4'>
                                        {card.ingredients?.map((ingredient, idx) => (
                                            <p key={idx} className='bg-white font-bold text-xl p-2'>
                                                {ingredient}
                                            </p>
                                        ))}
                                    </div>
                                </div>
                                <div className="w-full py-5 bg-white shadow-lg rounded-md">
                                    <div className='p-4'>
                                        <h1 className='text-teal-600 text-5xl font-bold'>Directions</h1>
                                        <p className='py-4'>{cardsData[currentIndex].direction}</p>
                                    </div>
                                </div>
 
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RecipeInfo;
