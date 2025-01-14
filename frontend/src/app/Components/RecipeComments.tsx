'use client';
import React, { useEffect, useState } from 'react';
import { useRouter, useParams } from 'next/navigation';
import { BASE_URL } from '@/config/apiConfig'; // Import the base URL
interface Comment {
    id: number;
    author: string;
    description: string;
    rating: number;
}

type RecipeCommentsProps = {
    comments: Comment[];
};

const RecipeComments: React.FC<RecipeCommentsProps> = ({ comments: initialComments }) => {

    const [comments, setComments] = useState<Comment[]>(initialComments);
    const [newComment, setNewComment] = useState<string>('');
    const [commentUser, setCommentUser] = useState<string | null>(null);
    const router = useRouter();
    const params = useParams();

    useEffect(() => {
        setComments(initialComments);
    }, [initialComments]);

    const fetchUserName = async (userID: string, token: string): Promise<string> => {
        try {
            const userIDLong = Number(userID);
            const userResponse = await fetch(`${BASE_URL}/user/${userIDLong}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (!userResponse.ok) {
                throw new Error('Failed to fetch username');
            }

            const userData = await userResponse.json();
            return userData.userName || 'Anonymous';
        } catch (error) {
            console.error('Error fetching username:', error);
            return 'Anonymous';
        }
    };

    const postComment = async (userName: string, newComment: string, params: any, token: string) => {
        try {
            const response = await fetch(`${BASE_URL}/reviews/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    recipeID: params.recipeId, // Assuming you have recipeId in params
                    author: userName,
                    description: newComment,
                    rating: 5,
                }),
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const result = await response.json();
            sessionStorage.setItem("session", JSON.stringify(result));
            return result;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    };

    const handleAddComment = async () => {
        if (newComment.trim() === '') {
            return;
        }

        const session = sessionStorage.getItem('session');
        if (!session) {
            router.push('/login');
            return;
        }

        const sessionData = JSON.parse(session);
        const token = sessionData.token;
        const userID = sessionData.userID;

        if (!token || !userID) {
            router.push('/login');
            return;
        }

        const userName = await fetchUserName(userID, token);
        setCommentUser(userName);

        try {
            const result = await postComment(userName, newComment, params, token);
            const nextId = comments.length > 0 ? comments[comments.length - 1].id + 1 : 1;
            const updatedComments = [
                ...comments,
                {
                    id: nextId,
                    author: userName,
                    description: newComment,
                    rating: 5, // Assuming a default rating
                },
            ];
            setComments(updatedComments);
            setNewComment('');
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <div className="w-full mx-auto p-6 bg-gray-100 rounded-lg shadow-lg">
            <div className="mb-6">
                <h2 className="text-2xl text-teal-700 font-semibold mb-4">Add a Comment</h2>
                <textarea
                    className="w-full p-3 mb-4 resize-none rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-teal-500"
                    rows={4}
                    placeholder="Write your comment here..."
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                />
                <button
                    className="bg-teal-600 text-white px-6 py-2 rounded-lg shadow hover:bg-teal-700 transition duration-300"
                    onClick={handleAddComment}
                >
                    Add Comment
                </button>
            </div>
            <div className="mt-6">
                {comments.length === 0 ? (
                    <p className="text-gray-500">No comments yet.</p>
                ) : (
                    comments.map((comment, index) => (
                        <div key={index} className="p-4 mb-4 bg-white rounded-lg shadow-sm border border-gray-200">
                            <p className="text-gray-800 font-semibold">{comment.author}</p>
                            <p className="text-gray-600 mt-1">{comment.description}</p>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default RecipeComments;
