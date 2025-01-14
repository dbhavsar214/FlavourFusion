'use client';
import React, { useState, useEffect } from 'react';
import Image from 'next/image';
import { useRouter } from 'next/navigation'; // Import useRouter
import { BASE_URL } from '@/config/apiConfig'; // Import the base URL
 
const UserRecommendation = () => {
    interface UserRecommends {
        id: number;
        imageURL: string;
        name: string;
        rating: number;
        reviews: number;
        description: string;
        tags: string[];
        readonly recipeID: number;
    }
 
    const initialData: UserRecommends[] = [];
    const [recommendationsDatacard, setRecommendationsDatacard] = useState<UserRecommends[]>(initialData);
    const [visibleItemCount, setVisibleItemCount] = useState(3);
    const router = useRouter(); // Initialize useRouter
 
    useEffect(() => {
        const fetchData = async () => {
            try {
                // Fetch data from your backend API
                const response = await fetch(`${BASE_URL}/recipes/all`);
                const data = await response.json();
                console.log("data", data);
                setRecommendationsDatacard(data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        fetchData();
    }, []);
 
 
    const loadMore = () => {
        setVisibleItemCount(prev => prev + 3);
    };
 
    const handleCardClick = (id: number) => {
        console.log("in userRecommentsdsac",id);
        
 
        router.push(`/recipe/${id}`); // Navigate to the recipe detail page
    };
 
    return (
        <div className="container mx-auto p-4">
            <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
                {recommendationsDatacard.slice(0, visibleItemCount).map((card, idx) => (
                    <div
                        key={idx}
                        className="max-w-sm bg-white border border-gray-200 rounded-lg shadow-lg overflow-hidden cursor-pointer transition-transform transform hover:scale-105"
                        onClick={() => handleCardClick(card.recipeID)}
                    >
                        <div className="relative h-48">
                            <Image
                                src="https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                                alt={card.name}
                                layout="fill"
                                objectFit="cover"
                                className="rounded-t-lg"
                            />
                        </div>
                        <div className="p-4">
                            <h5 className="text-xl font-semibold text-gray-900">{card.name}</h5>
                            <p className="mt-2 text-gray-600">{card.description}</p>
                            <div className="flex flex-wrap mt-3">
                                {card.tags.map((tag, tagIdx) => (
                                    <span key={tagIdx} className="inline-block bg-blue-100 text-teal-600 text-xs font-semibold px-2 py-1 mr-2 mb-2 rounded-full">{tag}</span>
                                ))}
                            </div>
                            <a href="#" className="block mt-4 bg-teal-600 hover:bg-teal-700 text-white text-center py-2 px-4 rounded-lg transition duration-300 ease-in-out">View Recipe</a>
                        </div>
                    </div>
                ))}
            </div>
            {visibleItemCount < recommendationsDatacard.length && (
                <div className="flex justify-center mt-4">
                    <button
                        onClick={loadMore}
                        className="bg-teal-600 hover:bg-teal-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-300 ease-in-out"
                    >
                        Load More
                    </button>
                </div>
            )}
        </div>
    );
 
};
 
export default UserRecommendation;