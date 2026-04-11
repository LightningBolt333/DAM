# Data Model

## DogResponse
The raw response from the Dog API.
- `message`: List<String> (Array of image URLs)
- `status`: String ("success" or "error")

## DogImage
The internal model used by the UI.
- `id`: String (UUID generated for RecyclerView DiffUtil)
- `imageUrl`: String