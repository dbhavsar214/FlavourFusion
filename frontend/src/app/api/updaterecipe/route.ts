import { NextRequest, NextResponse } from "next/server";
import { BASE_URL } from '@/config/apiConfig'; // Import the base URL
export async function POST(request: NextRequest) {

    try {
        const body = await request.json();
        console.log("route logs", body);

        // Forward the request to the backend
        const response = await fetch(`${BASE_URL}/addrecipe`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });

        const data = await response.json();

        return NextResponse.json({ message: "Data forwarded to backend", data: data });
    } catch (err) {
        console.log(err);
        return NextResponse.json({ error: "Failed to process request" }, { status: 500 });
    }
}