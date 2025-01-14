import axios from 'axios';
import { UserProfileInfo, AuthenticatedUserProfileInfo, AuthenticatedUserInfo, RecipeInfo } from '@/outputTypes/types';
import { BASE_URL } from '@/config/apiConfig'; // Import the base URL


const getAuthHeaders = () => {
    const sessionData = sessionStorage.getItem('session');
    if (sessionData) {
        const { token } = JSON.parse(sessionData);
        console.log("Token from session storage:", token); // Log to verify token
        return token ? { Authorization: `Bearer ${token}` } : {};
    }
    return {};
};

export const fetchUserProfile = async (userId: number): Promise<UserProfileInfo | null> => {
    try {
        const response = await axios.get(`${BASE_URL}/user/${userId}/profile`);
        if (!response.data) {
            return null;
        }
        const data: UserProfileInfo = {
            id: response.data.userID,
            imageURL: response.data.profileImage,
            name: response.data.userName,
            followers: response.data.followers ? response.data.followers.length : 0,
            description: response.data.bio,
            recipes: response.data.recipes ? response.data.recipes.map((recipe: any) => ({
                id: recipe.recipeID,
                imageURL: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
                name: recipe.name,
                rating: recipe.rating || null,
                reviews: recipe.reviews || null,
                description: recipe.description,
                tags: Array.isArray(recipe.tags) ? recipe.tags : []
            })) : []
        };
        return data;
    } catch (error) {
        console.error('Error fetching user profile:', error);
        return null;
    }
};

export const fetchAuthenticatedUserDetails = async (purpose: 'profile' | 'update'): Promise<AuthenticatedUserProfileInfo | AuthenticatedUserInfo | null> => {
    try {
        const headers = getAuthHeaders();
        console.log("Request headers:", headers); // Log headers

        const response = await axios.get(`${BASE_URL}/user/me`, { headers });
        console.log("Response:", response); // Log response

        if (!response.data) {
            return null;
        }

        const profileData: AuthenticatedUserProfileInfo = {
            id: response.data.userID,
            imageURL: response.data.profileImage,
            name: response.data.userName,
            followers: response.data.followers ? response.data.followers.length : 0,
            description: response.data.bio,
            recipes: response.data.recipes ? response.data.recipes.map((recipe: any) => ({
                id: recipe.recipeID,
                imageURL: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
                name: recipe.name,
                rating: recipe.rating || null,
                reviews: recipe.reviews || null,
                description: recipe.description,
                tags: Array.isArray(recipe.tags) ? recipe.tags : []
            })) : [],
            followedUserIds: response.data.followedUserIds || []
        };

        const updateData: AuthenticatedUserInfo = {
            id: response.data.userID,  // Add this line
            userName: response.data.userName,
            firstName: response.data.firstName,
            lastName: response.data.lastName,
            emailAddress: response.data.emailAddress,
            bio: response.data.bio
        };

        return purpose === 'profile' ? profileData : updateData;
    } catch (error) {
        console.error('Error fetching authenticated user details:', error);
        return null;
    }
};

export const fetchAuthenticatedUserRecipes = async (): Promise<RecipeInfo[]> => {
    try {
        const headers = getAuthHeaders();
        console.log('Request headers:', headers); // Log headers
        const response = await axios.get(`${BASE_URL}/user/me`, { headers });
        console.log("Response:", response); // Log response

        return response.data.recipes.map((recipe: any) => ({
            id: recipe.recipeID,
            imageURL: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
            name: recipe.name,
            rating: recipe.rating || null,
            reviews: recipe.reviews || null,
            description: recipe.description,
            tags: Array.isArray(recipe.tags) ? recipe.tags : []
        }));
    } catch (error) {
        console.error('Error fetching authenticated user recipes:', error);
        return [];
    }
};

export const updateUserProfile = async (userId: number, data: AuthenticatedUserInfo): Promise<void> => {
    try {
        await axios.put(`${BASE_URL}/user/${userId}/update`, data, { headers: getAuthHeaders() });
    } catch (error) {
        console.error('Error updating user profile:', error);
    }
};

export const followUser = async (userId: number, followerId: number): Promise<number[]> => {
    try {
        const headers = getAuthHeaders();
        console.log('Request headers:', headers); // Log headers
        const response = await axios.post(`${BASE_URL}/user/${userId}/follow/${followerId}`, {}, { headers });
        return response.data;
    } catch (error) {
        console.error('Error following user:', error);
        return [];
    }
};

export const unfollowUser = async (userId: number, followerId: number): Promise<number[]> => {
    try {
        const headers = getAuthHeaders();
        console.log('Request headers:', headers); // Log headers
        const response = await axios.delete(`${BASE_URL}/user/${userId}/unfollow/${followerId}`, { headers });
        return response.data;
    } catch (error) {
        console.error('Error unfollowing user:', error);
        return [];
    }
};
