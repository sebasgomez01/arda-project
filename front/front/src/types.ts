export type PostData = {
    title: string,
    textContent: string,
    user: string,
    imageBool: boolean,
    srcImage: string,
    id: string
}

export type PostCommentData = {
    textContent: string,
    post: {
        id: string
    }, 
    user: string   
}

export type NotificationData = {
  id: string;
  textContent: string;
  //createdAt: string;
  causedBy: { name: string; username: string };
  receivedBy: { name: string; username: string };
  post?: PostResponse;
  comment?: CommentResponse;
}


export type PostResponse = {
    user: {
        name: string,
        username: string
    },
    textContent: string,
    title: string,
    imagePath: string,
    _links: {
        dislikes: {
            href: string
        },
        likes: {
            href: string
        },
        post: {
            href: string
        },
        reposts: {
            href: string
        },
        self: {
            href: string
        },
        user: {
            href: string 
        },
   };
}

export type UserResponse = {
    name: string,
    username: string,
    _links: {
        dislikedPost: {
            href: string
        },
        followers: {
            href: string
        },
        likedPost: {
            href: string
        },
        notifications: {
            href: string
        },
        posts: {
            href: string
        },
        reposts: {
            href: string
        },
        self: {
            href: string
        },
        user: {
            href: string
        }
    }
}


export type CommentResponse = {
    id: string,
    textContent: string,
    user: {
        name: string,
        username: string
    }, 
    _links: {
        comment: {
            href: string
        },
        dislikes: {
            href: string
        },
        likes: {
            href: string
        },
        notifications: {
            href: string
        },
        parentComment: {
            href: string
        },
        post: {
            href: string 
        },
        replies: {
            href: string 
        },
        self: {
            href: string 
        },
        user: {
            href: string 
        },
   };

}
