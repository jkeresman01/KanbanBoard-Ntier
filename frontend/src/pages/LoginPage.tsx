import {
    Box,
    Button,
    Flex,
    FormControl,
    FormLabel,
    Heading,
    Input,
    Link,
    Stack,
    Text,
    useColorModeValue,
} from "@chakra-ui/react";

export default function LoginPage() {
    return (
        <Flex
            minH="100vh"
            align="center"
            justify="center"
            bg={useColorModeValue("gray.50", "gray.800")}
        >
            <Stack spacing={8} mx="auto" maxW="lg" py={12} px={6}>
                <Stack align="center">
                    <Heading fontSize="4xl">Sign in to your account</Heading>
                    <Text fontSize="lg" color="gray.600">
                        to access your Kanban board ✌️
                    </Text>
                </Stack>
                <Box rounded="lg" bg={useColorModeValue("white", "gray.700")} boxShadow="lg" p={8}>
                    <Stack spacing={4}>
                        <FormControl id="usernameOrEmail">
                            <FormLabel>Email or Username</FormLabel>
                            <Input type="text" />
                        </FormControl>
                        <FormControl id="password">
                            <FormLabel>Password</FormLabel>
                            <Input type="password" />
                        </FormControl>
                        <Stack spacing={10}>
                            <Button bg="blue.400" color="white" _hover={{ bg: "blue.500" }}>
                                Sign in
                            </Button>
                            <Text align="center">
                                Don’t have an account?{" "}
                                <Link color="blue.400" href="/register">
                                    Register
                                </Link>
                            </Text>
                        </Stack>
                    </Stack>
                </Box>
            </Stack>
        </Flex>
    );
}
