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

export default function RegisterPage() {
    return (
        <Flex
            minH="100vh"
            align="center"
            justify="center"
            bg={useColorModeValue("gray.50", "gray.800")}
        >
            <Stack spacing={8} mx="auto" maxW="lg" py={12} px={6}>
                <Stack align="center">
                    <Heading fontSize="4xl">Create your account</Heading>
                </Stack>
                <Box rounded="lg" bg={useColorModeValue("white", "gray.700")} boxShadow="lg" p={8}>
                    <Stack spacing={4}>
                        <FormControl id="username">
                            <FormLabel>Username</FormLabel>
                            <Input type="text" />
                        </FormControl>
                        <FormControl id="email">
                            <FormLabel>Email</FormLabel>
                            <Input type="email" />
                        </FormControl>
                        <FormControl id="password">
                            <FormLabel>Password</FormLabel>
                            <Input type="password" />
                        </FormControl>
                        <Stack spacing={10}>
                            <Button bg="green.400" color="white" _hover={{ bg: "green.500" }}>
                                Register
                            </Button>
                            <Text align="center">
                                Already have an account?{" "}
                                <Link color="blue.400" href="/login">
                                    Login
                                </Link>
                            </Text>
                        </Stack>
                    </Stack>
                </Box>
            </Stack>
        </Flex>
    );
}
