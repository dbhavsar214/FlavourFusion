import axios from 'axios';
import { SearchResultRecipe } from '@/outputTypes/types';
import { BASE_URL } from '@/config/apiConfig'; // Import the base URL



export const search = async (name: string, tag: string, ingredient: string): Promise<SearchResultRecipe[]> => {
    try {
        console.log('Sending to backend:', { name, tag, ingredient });  // Add this line for logging
        const response = await axios.get(`${BASE_URL}/search`, { params: { name, tag, ingredient } });
        console.log('Backend response:', response.data);  // Add this line for logging
        return response.data.map((recipe: any) => ({
            ...recipe,
            imageURL: recipe.imageURL || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'
        }));
    } catch (error) {
        console.error('Error searching recipes:', error);
        throw error;
    }
};
